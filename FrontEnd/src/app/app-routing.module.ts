import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AdminLayoutComponent } from './BackOffice/layouts/admin-layout/admin-layout.component';
import { IndexComponent } from './FrontOffice/index/index.component';
import { BlogComponent } from './FrontOffice/blog/blog.component';
import { ContactComponent } from './FrontOffice/contact/contact.component';
import { PortfolioComponent } from './FrontOffice/portfolio/portfolio.component';
import { ServiceComponent } from './FrontOffice/service/service.component';
import { SingleComponent } from './FrontOffice/single/single.component';
import { TeamComponent } from './FrontOffice/team/team.component';
import { AboutComponent } from './FrontOffice/about/about.component';
import { HomeComponent } from './BackOffice/home/home.component';
import { UserComponent } from './BackOffice/user/user.component';
import { TestComponent } from './BackOffice/test/test.component';
import { MapsComponent } from './BackOffice/maps/maps.component';
import { TablesComponent } from './BackOffice/tables/tables.component';
import { TypographyComponent } from './BackOffice/typography/typography.component';
import { IconsComponent } from './BackOffice/icons/icons.component';
import { NotificationsComponent } from './BackOffice/notifications/notifications.component';
import { UpgradeComponent } from './BackOffice/upgrade/upgrade.component';
import { PostTacheComponent } from './tache/post-tache/post-tache.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientJsonpModule, HttpClientModule } from '@angular/common/http';
import { GetAllTacheComponent } from './tache/get-all-tache/get-all-tache.component';
import { UpdateTacheComponent } from './tache/update-tache/update-tache.component';
import { KanbanComponent } from './tache/kanban/kanban.component';
import { TacheResponseComponent } from './tache/tache-response/tache-response.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { verifyHostBindings } from '@angular/compiler';
import { VerifyEmailComponent } from './auth/verify-email/verify-email.component';
import { GetofferComponent } from './FrontOffice/Offer/getoffer/getoffer.component';
import { AddofferComponent } from './FrontOffice/Offer/addoffer/addoffer.component';
import { UpdateofferComponent } from './FrontOffice/Offer/updateoffer/updateoffer.component';
import { ViewOffersComponent } from './FrontOffice/Offer/view-offers/view-offers.component';
import { ChartsComponent } from './BackOffice/charts/charts.component';
import { InsuranceComponent } from './BackOffice/insurance/insurance.component';
import { DashboardComponent } from './BackOffice/dashboard/dashboard.component';
import { InsuranceChartsComponent } from './BackOffice/insurance/insurance-charts/insurance-charts.component';
import { RequestManagementComponent } from './BackOffice/request-management/request-management.component';
import { RequestDetailsComponent } from './BackOffice/request-details/request-details.component';
import { PostRequestComponent } from './projects/post-request/post-request.component';
import { GetAllRequestComponent } from './projects/get-all-request/get-all-request.component';
import { UpdateRequestComponent } from './projects/update-request/update-request.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { UpdateAdvanceComponent } from './FrontOffice/advance/update-advance/update-advance.component';
import { AdvanceBackComponent } from './BackOffice/Advance/advance-back/advance-back.component'
import { SafeHtmlPipe } from './pipes/safe-html.pipe';
import { NgChartsModule } from 'ng2-charts';
import { PieChartComponent } from './BackOffice/AdvanceCharts/pie-chart/pie-chart.component';
import { SinusoidalComponent } from './BackOffice/AdvanceCharts/pie-chart/sinusoidal/sinusoidal.component';
import { BarChartComponent } from './BackOffice/AdvanceCharts/pie-chart/bar-chart/bar-chart.component';
import { AddAdvanceComponent } from './FrontOffice/advance/add-advance/add-advance.component';
import { StockComponent } from './BackOffice/stock/stock.component';
import { StockEditComponent } from './BackOffice/stock/stock-edit/stock-edit.component';

// Define your routes
const routes: Routes = [
  // FrontOffice routes
  { path: '', component: IndexComponent },
  { path: 'blog', component: BlogComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'portfolio', component: PortfolioComponent },
  { path: 'service', component: ServiceComponent },
  { path: 'single', component: SingleComponent },
  { path: 'team', component: TeamComponent },
  { path: 'test', component: TestComponent },
  {path:'tache',component:PostTacheComponent},
  {path:'get-all-tache',component:GetAllTacheComponent},
  {path:'tache/:id',component:UpdateTacheComponent},
{path:'kanban',component:KanbanComponent},
{path:'user',component:UserComponent},
{path:'login',component:LoginComponent},
{path:'signup',component:SignupComponent},
{ path: 'forgot-password', component: ForgotPasswordComponent },
{ path: 'verify', component:  VerifyEmailComponent},
{
  path: 'reset-password',
  component: ResetPasswordComponent
},
{path:'app-view-offers', component:ViewOffersComponent},
{path:'request',component:PostRequestComponent},
{path:'all-request',component:GetAllRequestComponent},
{path:'request/:id_projet',component:UpdateRequestComponent},
{path: 'advance', component:AddAdvanceComponent},
  {path: 'add-advance', component:AddAdvanceComponent},
  {path: 'update-advance/:id', component:UpdateAdvanceComponent},

// Add other backoffice child routes here
  // BackOffice routes
  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
          { path: 'dashboard',      component: HomeComponent },
         {path: 'admin2',          component: AdminLayoutComponent },
         { path: 'user',           component: UserComponent },
         { path: 'table',          component: TablesComponent },
         { path: 'typography',     component: TypographyComponent },
         { path: 'icons',          component: IconsComponent },
         { path: 'maps',           component: MapsComponent },
         { path: 'notifications',  component: NotificationsComponent },
         { path: 'upgrade',        component: UpgradeComponent },
         { path: 'addoffer',component:AddofferComponent},
         { path: 'getoffer',component:GetofferComponent},
         { path: 'updateoffer/:id', component: UpdateofferComponent },
         {path: 'app-charts', component: ChartsComponent},
         {path: 'app-dashboard',component: DashboardComponent},
         {path: 'insurance', component: InsuranceComponent},
         { path: 'insurance-charts', component: InsuranceChartsComponent },
         { path: 'maps',           component: MapsComponent },
         { path: 'notifications',  component: NotificationsComponent },
         { path: 'upgrade',        component: UpgradeComponent },
         { path: 'project-manager', component:RequestManagementComponent  },
         { path: 'request-details/:id_projet', component: RequestDetailsComponent },
         {path:'advanceback', component:AdvanceBackComponent},
         {path:'advanceback', component:AdvanceBackComponent},
         {path:'app-pie-chart', component:PieChartComponent},
         {path:'app-bar-chart', component:BarChartComponent},
         {path:'app-sinusoidal',component:SinusoidalComponent},
         { path: 'stock',          component: StockComponent },
         { path: 'stock/edit/:id', component: StockEditComponent },
         {path:'app-dashboard',component: DashboardComponent},
    
         // Add other backoffice child routes here
      { path: '', redirectTo: 'home', pathMatch: 'full' }, // Default route under admin layout
    ],
  },
  { path: 'reponse/:taskId/:response', component: TacheResponseComponent },
  // Default route for unknown paths
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes, {
      useHash: true, // Use hash-based routing (optional)
    }),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
