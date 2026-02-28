import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-platform-shell',
    standalone: true,
    imports: [
        CommonModule,
        RouterModule,
        TranslateModule,
        MatCardModule,
        MatIconModule,
        MatButtonModule
    ],
    template: `
    <div class="shell-container p-4">
      <mat-card class="welcome-card card-gradient mb-4">
        <mat-card-header>
          <div mat-card-avatar>
            <mat-icon class="header-icon">{{ icon }}</mat-icon>
          </div>
          <mat-card-title>{{ title | translate }}</mat-card-title>
          <mat-card-subtitle>{{ 'platform.shell.description' | translate }}</mat-card-subtitle>
        </mat-card-header>
        <mat-card-content class="mt-4">
          <div class="wip-container text-center py-5">
            <mat-icon class="wip-icon">engineering</mat-icon>
            <h2 class="mt-3">{{ 'platform.shell.wip_title' | translate }}</h2>
            <p class="text-secondary">{{ 'platform.shell.wip_message' | translate }}</p>
            <button mat-stroked-button color="primary" class="mt-3" routerLink="/platform/dashboard">
              <mat-icon>arrow_back</mat-icon>
              {{ 'platform.shell.back_dashboard' | translate }}
            </button>
          </div>
        </mat-card-content>
      </mat-card>
    </div>
  `,
    styles: [`
    .shell-container {
      max-width: 1200px;
      margin: 0 auto;
    }
    .header-icon {
      font-size: 40px;
      width: 40px;
      height: 40px;
      color: white;
    }
    .wip-icon {
      font-size: 64px;
      width: 64px;
      height: 64px;
      color: #ffd740;
    }
    .card-gradient {
      background: linear-gradient(135deg, #1e3a8a 0%, #3b82f6 100%);
      color: white;
      ::ng-deep .mat-mdc-card-subtitle {
        color: rgba(255, 255, 255, 0.8) !important;
      }
    }
    .wip-container {
      background: rgba(255, 255, 255, 0.05);
      border-radius: 12px;
      border: 1px dashed rgba(255, 255, 255, 0.2);
    }
  `]
})
export class PlatformShellComponent implements OnInit {
    private route = inject(ActivatedRoute);

    title = 'menu.dashboard';
    icon = 'dashboard';

    ngOnInit(): void {
        this.route.data.subscribe(data => {
            if (data['title']) this.title = data['title'];
            if (data['icon']) this.icon = data['icon'];
        });
    }
}
