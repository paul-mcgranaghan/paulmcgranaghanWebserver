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
import {AuthModule} from "@auth0/auth0-angular";
import {AuthButtonComponent} from './auth-button/auth-button.component';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserFormComponent,
    AuthButtonComponent
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
    MatPseudoCheckboxModule,

    // Import the module into the application, with configuration
    AuthModule.forRoot({
      domain: 'dev-k8m0dma6.eu.auth0.com',
      clientId: 'Kx1nzicJj2MWMELFY7oRR8t6RKGStpCV'
    }),
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
