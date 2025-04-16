
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AboutComponent } from './FrontOffice/about/about.component';
import { BlogComponent } from './FrontOffice/blog/blog.component';
import { IndexComponent } from './FrontOffice/index/index.component';
import { ContactComponent } from './FrontOffice/contact/contact.component';
import { PortfolioComponent } from './FrontOffice/portfolio/portfolio.component';
import { ServiceComponent } from './FrontOffice/service/service.component';
import { SingleComponent } from './FrontOffice/single/single.component';
import { TeamComponent } from './FrontOffice/team/team.component';
import { AdminLayoutComponent } from './BackOffice/layouts/admin-layout/admin-layout.component';
import { ReactiveFormsModule } from '@angular/forms'; // ✅ Importer ReactiveFormsModule
import { NavbarModule } from './BackOffice/shared/navbar/navbar.module';
import { FooterModule } from './BackOffice/shared/footer/footer.module';
import { SidebarModule } from './BackOffice/sidebar/sidebar.module';
import { AdminLayoutModule } from './BackOffice/layouts/admin-layout/admin-layout.module';
import { TestComponent } from './BackOffice/test/test.component';
import { TacheComponent } from './tache/tache.component';
import { PostTacheComponent } from './tache/post-tache/post-tache.component';
import { DeleteTacheComponent } from './tache/delete-tache/delete-tache.component';
import { UpdateTacheComponent } from './tache/update-tache/update-tache.component';
import { GetAllTacheComponent } from './tache/get-all-tache/get-all-tache.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { KanbanComponent } from './tache/kanban/kanban.component';
import { DatePipe } from '@angular/common';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import{ ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { VerifyEmailComponent } from './auth/verify-email/verify-email.component';  
import { PipesPipe } from './FrontOffice/pipes.pipe';
import{ ViewOffersComponent } from './FrontOffice/Offer/view-offers/view-offers.component'; 
import { EditorModule } from '@tinymce/tinymce-angular'
import { GetofferComponent } from './FrontOffice/Offer/getoffer/getoffer.component';
import { AddofferComponent } from './FrontOffice/Offer/addoffer/addoffer.component';
import { InsuranceChartsComponent } from './BackOffice/insurance/insurance-charts/insurance-charts.component';
import { InsuranceComponent } from './BackOffice/insurance/insurance.component';
import{ DashboardComponent } from './BackOffice/dashboard/dashboard.component';
import { ChartsComponent } from './BackOffice/OfferCharts/charts/charts.component';
import { UpdateofferComponent } from './FrontOffice/Offer/updateoffer/updateoffer.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { PostRequestComponent } from './projects/post-request/post-request.component';
import { GetAllRequestComponent } from './projects/get-all-request/get-all-request.component';
import { UpdateRequestComponent } from './projects/update-request/update-request.component';
import { RequestManagementComponent } from './BackOffice/request-management/request-management.component';
import { RequestDetailsComponent } from './BackOffice/request-details/request-details.component';
import { MatStepperModule } from '@angular/material/stepper';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

import { AddAdvanceComponent } from './FrontOffice/advance/add-advance/add-advance.component';
import { UpdateAdvanceComponent } from './FrontOffice/advance/update-advance/update-advance.component';
import { AdvanceBackComponent } from './BackOffice/Advance/advance-back/advance-back.component';
import { SafeHtmlPipe } from './pipes/safe-html.pipe';
import { NgChartsModule } from 'ng2-charts';
import { PieChartComponent } from './BackOffice/AdvanceCharts/pie-chart/pie-chart.component';
import { SinusoidalComponent } from './BackOffice/AdvanceCharts/pie-chart/sinusoidal/sinusoidal.component';
import { BarChartComponent } from './BackOffice/AdvanceCharts/pie-chart/bar-chart/bar-chart.component';
import { StockComponent } from './BackOffice/stock/stock.component';
import { StockEditComponent } from './BackOffice/stock/stock-edit/stock-edit.component';
import { map } from 'jquery';

import { PaginatePipe } from 'ngx-pagination';
import { NgxPaginationModule } from 'ngx-pagination'; // Add this import
import { MapsComponent } from './BackOffice/maps/maps.component';


@NgModule({
 
 
  declarations: [
    AppComponent,
    AboutComponent,
    BlogComponent,
    IndexComponent,
    ContactComponent,
    PortfolioComponent,
    ServiceComponent,
    SingleComponent,
    TeamComponent,
    AdminLayoutComponent,
    TestComponent,
    TacheComponent,
    PostTacheComponent,
    DeleteTacheComponent,
    UpdateTacheComponent,
    GetAllTacheComponent,
    KanbanComponent,
    UserComponent,
    LoginComponent,
    SignupComponent,
    ForgotPasswordComponent,
    VerifyEmailComponent,
    PipesPipe,
    ResetPasswordComponent,
    ViewOffersComponent,
UpdateTacheComponent,
PipesPipe,
UpdateofferComponent,
GetofferComponent,
AddofferComponent,
InsuranceComponent,
InsuranceChartsComponent,
ChartsComponent,
DashboardComponent,
    GetAllTacheComponent,
    TacheComponent,
    DeleteTacheComponent,
    KanbanComponent,
    PostTacheComponent,
    UpdateTacheComponent,
 GetofferComponent,
 PostRequestComponent,
 GetAllRequestComponent,
 UpdateRequestComponent,
 RequestManagementComponent,
 RequestDetailsComponent,
 AppComponent,
 AboutComponent,

 IndexComponent,
 ContactComponent,
 PortfolioComponent,
 ServiceComponent,
 
 TeamComponent,
 AdminLayoutComponent,
 TestComponent,
    AddAdvanceComponent,
    UpdateAdvanceComponent,
    AdvanceBackComponent,
    SafeHtmlPipe,
    DashboardComponent,
    PieChartComponent,
    BarChartComponent,
    SinusoidalComponent,
AdvanceBackComponent,
    RequestDetailsComponent,
    RequestManagementComponent,
    StockEditComponent,
    StockComponent,
    MapsComponent,
 

   
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    CommonModule,
    AppRoutingModule,
    NavbarModule,
    FooterModule,
    SidebarModule,
    ReactiveFormsModule ,
    HttpClientModule,
    DragDropModule,
    BrowserAnimationsModule,
    DragDropModule ,
    FormsModule,
    BrowserModule,  // This already includes CommonModule for the root module
    CommonModule, 
    EditorModule,
    NgxChartsModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    CommonModule,
    AppRoutingModule,
    NavbarModule,
    FooterModule,
    SidebarModule,
    ReactiveFormsModule,
    DragDropModule,
   BrowserAnimationsModule,
   MatStepperModule,
   MatInputModule,
   MatButtonModule,
   MatFormFieldModule,
   MatSelectModule,
   BrowserModule,
   ReactiveFormsModule,
   FormsModule,
   RouterModule,
   MatStepperModule,
   MatFormFieldModule,
   MatInputModule,
   NgChartsModule,
   NgChartsModule,
   BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    CommonModule,
    AppRoutingModule,
    NavbarModule,
    FooterModule,
    SidebarModule,
    EditorModule,
    NgxPaginationModule,
    
  ],
   // Add this
   providers: [DatePipe],
  bootstrap: [AppComponent],  
  exports: [StockEditComponent,
    PipesPipe
  ] ,schemas: [CUSTOM_ELEMENTS_SCHEMA]  // Add this line
})
export class AppModule {}