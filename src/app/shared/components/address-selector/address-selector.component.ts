import {
    Component,
    Input,
    OnInit,
    OnDestroy,
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    inject
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { TranslateModule } from '@ngx-translate/core';
import { forkJoin, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { Country } from 'app/api/models/country';
import { Departamento } from 'app/api/models/departamento';
import { Provincia } from 'app/api/models/provincia';
import { Municipio } from 'app/api/models/municipio';
import { CountryService } from 'app/api/services/country.service';
import { DepartamentoService } from 'app/api/services/departamento.service';
import { ProvinciaService } from 'app/api/services/provincia.service';
import { MunicipioService } from 'app/api/services/municipio.service';

/**
 * AddressSelectorComponent
 *
 * Shared reusable component for address input with cascading geography selects:
 *   País → Departamento → Provincia → Municipio
 *
 * Usage:
 *   <app-address-selector [addressForm]="myAddressFormGroup"></app-address-selector>
 *
 * The parent is responsible for creating the FormGroup with the address controls.
 * Use AddressSelectorComponent.buildFormGroup(fb) to get a correctly structured FormGroup.
 *
 * Fields managed:
 *   - countryId     (number)  → maps to Address.countryId
 *   - departamentoId (number) → maps to Address.departamentoId
 *   - provinciaId   (number)  → maps to Address.provinciaId
 *   - municipioId   (number)  → maps to Address.municipioId
 *   - ciudad        (string)  → maps to Address.ciudad (free text, pre-filled from municipio.nombre)
 *   - direccion     (string)  → maps to Address.direccion (required)
 *   - numero        (string)  → maps to Address.numero
 *   - piso          (number)  → maps to Address.piso
 *   - casillaPostal (string)  → maps to Address.casillaPostal
 */
@Component({
    selector: 'app-address-selector',
    standalone: true,
    templateUrl: './address-selector.component.html',
    styleUrls: ['./address-selector.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule,
        MatIconModule,
        TranslateModule
    ]
})
export class AddressSelectorComponent implements OnInit, OnDestroy {

    /** The FormGroup with address controls — built by parent using buildFormGroup() */
    @Input({ required: true }) addressForm!: FormGroup;

    /**
     * Factory method — call this in the parent component to create the address sub-group.
     * Example:
     *   this.addressForm = AddressSelectorComponent.buildAddressFormGroup(fb);
     *   this.mainForm = fb.group({ ..., address: this.addressForm });
     */
    static buildAddressFormGroup(fb: FormBuilder): FormGroup {
        return fb.group({
            // Geography cascade — UI controls
            countryId: [null],
            departamentoId: [null],
            provinciaId: [null],
            municipioId: [null],
            // Address text fields — mapped to Address model
            ciudad: [''],
            direccion: ['', Validators.required],
            numero: [''],
            piso: [null],
            casillaPostal: [''],
        });
    }

    // ── Geography data (loaded once) ────────────────────────────────────────
    countries: Country[] = [];
    allDepartamentos: Departamento[] = [];
    allProvincias: Provincia[] = [];
    allMunicipios: Municipio[] = [];

    // ── Filtered lists (reactive to cascade selection) ───────────────────────
    filteredDepartamentos: Departamento[] = [];
    filteredProvincias: Provincia[] = [];
    filteredMunicipios: Municipio[] = [];

    loading = true;

    private destroy$ = new Subject<void>();
    private cdr = inject(ChangeDetectorRef);
    private countryService = inject(CountryService);
    private departamentoService = inject(DepartamentoService);
    private provinciaService = inject(ProvinciaService);
    private municipioService = inject(MunicipioService);

    ngOnInit(): void {
        this.loadGeographyData();
    }

    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }

    private loadGeographyData(): void {
        forkJoin({
            countries: this.countryService.getAllCountries(),
            departamentos: this.departamentoService.getAllDepartamentos(),
            provincias: this.provinciaService.getAllProvincias(),
            municipios: this.municipioService.getAllMunicipios()
        }).pipe(takeUntil(this.destroy$)).subscribe({
            next: ({ countries, departamentos, provincias, municipios }) => {
                this.countries = countries.sort((a, b) => a.niceName.localeCompare(b.niceName));
                this.allDepartamentos = departamentos;
                this.allProvincias = provincias;
                this.allMunicipios = municipios;
                this.loading = false;

                // If editing (pre-populated values), restore initial cascade lists
                this.restoreCascadeFromFormValues();
                this.setupCascadeListeners();

                // Ensure OnPush detects initial loaded data
                this.cdr.markForCheck();
            },
            error: () => {
                this.loading = false;
                this.cdr.markForCheck();
            }
        });

        // Global listener to ensure OnPush detects ANY external patchValue
        this.addressForm.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
            this.cdr.markForCheck();
        });
    }

    /** 
     * Handles manual user interaction from the UI.
     * Resets dependent fields only when the user explicitly changes a value.
     */
    onCountryUserChange(countryId: number | null): void {
        this.addressForm.patchValue({
            departamentoId: null,
            provinciaId: null,
            municipioId: null,
            ciudad: ''
        });
        // Filtering is handled by the reactive listener on countryId
    }

    onDepartamentoUserChange(departamentoId: number | null): void {
        this.addressForm.patchValue({
            provinciaId: null,
            municipioId: null,
            ciudad: ''
        });
    }

    onProvinciaUserChange(provinciaId: number | null): void {
        this.addressForm.patchValue({
            municipioId: null,
            ciudad: ''
        });
    }

    /** 
     * Set up valueChanges listeners to update filtered lists.
     * IMPORTANT: These listeners do NOT reset values to avoid wiping out data during patchValue.
     */
    private setupCascadeListeners(): void {
        // Country → filter Departamentos
        this.addressForm.get('countryId')?.valueChanges
            .pipe(takeUntil(this.destroy$))
            .subscribe((countryId: number | null) => {
                this.filteredDepartamentos = countryId
                    ? this.allDepartamentos.filter(d => d.countryId === countryId)
                    : [];
                this.cdr.markForCheck();
            });

        // Departamento → filter Provincias
        this.addressForm.get('departamentoId')?.valueChanges
            .pipe(takeUntil(this.destroy$))
            .subscribe((departamentoId: number | null) => {
                this.filteredProvincias = departamentoId
                    ? this.allProvincias.filter(p => p.departamentoId === departamentoId)
                    : [];
                this.cdr.markForCheck();
            });

        // Provincia → filter Municipios
        this.addressForm.get('provinciaId')?.valueChanges
            .pipe(takeUntil(this.destroy$))
            .subscribe((provinciaId: number | null) => {
                this.filteredMunicipios = provinciaId
                    ? this.allMunicipios.filter(m => m.provinciaId === provinciaId)
                    : [];
                this.cdr.markForCheck();
            });
    }


    /** When editing an entity, pre-fill the cascade state from the form values */
    private restoreCascadeFromFormValues(): void {
        const countryId = this.addressForm.get('countryId')?.value;
        const departamentoId = this.addressForm.get('departamentoId')?.value;
        const provinciaId = this.addressForm.get('provinciaId')?.value;

        if (countryId) {
            this.filteredDepartamentos = this.allDepartamentos.filter(d => d.countryId === countryId);
        }
        if (departamentoId) {
            this.filteredProvincias = this.allProvincias.filter(p => p.departamentoId === departamentoId);
        }
        if (provinciaId) {
            this.filteredMunicipios = this.allMunicipios.filter(m => m.provinciaId === provinciaId);
        }
    }
}
