import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UserListComponent} from './user-list/user-list.component';
import {UserFormComponent} from './user-form/user-form.component';
import {UserService} from './service/user/user.service';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {RouterModule} from '@angular/router';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatPseudoCheckboxModule} from "@angular/material/core";

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatIconModule,
    RouterModule.forRoot([]),
    MatSlideToggleModule,
    ReactiveFormsModule,
    MatPseudoCheckboxModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
