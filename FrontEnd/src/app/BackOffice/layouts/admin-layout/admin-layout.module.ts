import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgChartsModule } from 'ng2-charts';

import { LbdModule } from '../../lbd/lbd.module';
import { NguiMapModule } from '@ngui/map';

import { AdminLayoutRoutes } from './admin-layout.routing';

import { HomeComponent } from '../../home/home.component';
import { UserComponent } from '../../user/user.component';
import { TablesComponent } from '../../tables/tables.component';
import { TypographyComponent } from '../../typography/typography.component';
import { IconsComponent } from '../../icons/icons.component';
import { MapsComponent } from '../../maps/maps.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { UpgradeComponent } from '../../upgrade/upgrade.component';
import { NavbarModule } from "../../shared/navbar/navbar.module";
import { FooterModule } from "../../shared/footer/footer.module";
import { SidebarModule } from "../../sidebar/sidebar.module";
import { PieChartComponent } from '../../AdvanceCharts/pie-chart/pie-chart.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    LbdModule,
    HomeComponent,
    NavbarModule,
    FooterModule,
    SidebarModule,
    NgChartsModule,
   
  ],
  declarations: [
    UserComponent,
    TablesComponent,
    TypographyComponent,
    IconsComponent,
    NotificationsComponent,
    UpgradeComponent,
    MapsComponent,
  ]
})
export class AdminLayoutModule {}