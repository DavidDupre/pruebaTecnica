import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TableComponent } from '../../table/table.component';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { NgForm } from '@angular/forms'; // Importa NgForm

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    TableComponent,
    MatTableModule,
    CommonModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  @Output() usuariosActualizados = new EventEmitter<void>();
  @ViewChild('registroForm', { static: false }) registroForm: NgForm | undefined; // Acceder al formulario
  @ViewChild(TableComponent) tableComponent: TableComponent | undefined;

  usuario = {
    id: null,
    nombre: '',
    email: '',
    cargo: 0,
    fechaIngreso: '',
  };

  esEdicion = false;

  constructor(private http: HttpClient) {
    this.cargarRoles();
  }

  recibirUsuario(usuario: any) {
    this.usuario = { ...usuario };
    this.esEdicion = true;
    this.usuario.cargo = usuario.rol.id;
  }

  refreshUsuarios() {
    if (this.tableComponent) {
      this.tableComponent.cargarUsuarios();
    }
  }

  onSubmit() {
    console.log('Usuario enviado:', this.usuario);

    if (this.usuario.cargo === null || this.usuario.cargo === undefined) {
      this.usuario.cargo = 0;
    }

    const id = this.usuario.id;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const apiUrl = `http://localhost:8080/usuarios/editar/${id}`;

    if (this.esEdicion) {
      this.http.put(apiUrl, this.usuario, { headers, responseType: 'text' }).subscribe(
        (response) => {
          console.log('Usuario editado con éxito:', response);
          this.usuariosActualizados.emit();
          this.refreshUsuarios();
          this.resetForm(); // Llamar a reset después de editar
        }
      );
    } else {
      this.http.post('http://localhost:8080/usuarios/registrar', this.usuario, { headers }).subscribe(
        (response) => {
          console.log('Usuario registrado con éxito:', response);
          this.usuariosActualizados.emit();
          this.refreshUsuarios();
          this.resetForm(); // Llamar a reset después de registrar
        }
      );
    }
  }

  resetForm() {
    // Restablecer el objeto usuario a su estado inicial
    this.usuario = {
      id: null,
      nombre: '',
      email: '',
      cargo: 0,
      fechaIngreso: '',
    };
    this.esEdicion = false;

    // Restablecer el formulario
    if (this.registroForm) {
      this.registroForm.reset();
    }
  }

  roles: any[] = [];
  cargarRoles() {
    this.http.post<any[]>('http://localhost:8080/usuarios/roles', null).subscribe(
      (data) => {
        console.log('Roles cargados:', data); // Verifica si los roles están llegando correctamente
        this.roles = data;
      },
      (error) => {
        console.error('Error al cargar roles:', error);
      }
    );
  }
}
