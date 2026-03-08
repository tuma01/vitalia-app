import { Component, Input, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule, Validators, FormBuilder } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { TranslateModule } from '@ngx-translate/core';

export interface PersonFormConfig {
    // Identification
    showIdentification?: boolean;
    isIdentificationRequired?: boolean;

    // Names
    showMiddleName?: boolean;
    showMaidenName?: boolean; // Apellido Materno

    // Demographics
    showBirthDate?: boolean;
    isBirthDateRequired?: boolean;
    showGender?: boolean;
    isGenderRequired?: boolean;
    showCivilStatus?: boolean;

    // Contact (Intermediate fields)
    showPhone?: boolean;
    isPhoneRequired?: boolean;
    showPersonalEmail?: boolean; // Email personal vs email de acceso
}

@Component({
    selector: 'app-person-form',
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatDatepickerModule,
        MatIconModule,
        TranslateModule
    ],
    templateUrl: './person-form.component.html',
    styleUrls: ['./person-form.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class PersonFormComponent {
    @Input({ required: true }) parentForm!: FormGroup;
    @Input({ required: true }) config: PersonFormConfig = {};

    /**
     * Factory method to build the required controls in the parent form.
     * Use this to ensure the parent form has the correct structure.
     */
    static buildPersonFormGroup(fb: FormBuilder, config: PersonFormConfig): FormGroup {
        const group: any = {
            nombre: ['', Validators.required],
            apellidoPaterno: ['', Validators.required]
        };

        if (config.showIdentification) {
            group.documentType = [null, config.isIdentificationRequired ? Validators.required : null];
            group.nationalId = ['', config.isIdentificationRequired ? Validators.required : null];
        }

        if (config.showMiddleName) group.segundoNombre = [''];
        if (config.showMaidenName) group.apellidoMaterno = [''];

        if (config.showBirthDate) {
            group.fechaNacimiento = [null, config.isBirthDateRequired ? Validators.required : null];
        }

        if (config.showGender) {
            group.genero = [null, config.isGenderRequired ? Validators.required : null];
        }

        if (config.showCivilStatus) group.estadoCivil = [null];

        if (config.showPhone) {
            group.telefono = ['', config.isPhoneRequired ? Validators.required : null];
        }

        if (config.showPersonalEmail) group.email = ['', Validators.email];

        return fb.group(group);
    }
}
