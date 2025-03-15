import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { UsuarioService } from './usuario.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    BrowserModule,
    MatSnackBarModule,
    FormsModule,
  ],
  providers: [
    UsuarioService,
    provideHttpClient(withInterceptorsFromDi()),
  ],
  bootstrap: [],
})
export class AppModule {}
