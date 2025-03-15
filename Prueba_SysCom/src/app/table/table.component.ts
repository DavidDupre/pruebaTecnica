import { AfterViewInit, Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { UsuarioService } from '../usuario.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
  imports: [MatTableModule, MatPaginatorModule],
  standalone: true,
})
export class TableComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = ['id', 'nombre', 'email', 'cargo', 'fechaIngreso', 'diasTrabajados', 'contrato', 'acciones'];

  dataSource = new MatTableDataSource<any>([]);

  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;

  @Output() usuarioSeleccionado = new EventEmitter<any>();

  ngAfterViewInit(): void {
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
    }
  }

  constructor(
    private usuarioService: UsuarioService,
    private http: HttpClient,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.usuarioService.getUsuarios().subscribe(
      (data) => {
        console.log('Datos de usuarios:', data);
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
      },
      (error) => {
        console.error('Error al cargar usuarios:', error);
      }
    );
  }

  refreshUsuarios(): void {
    this.cargarUsuarios();
  }

  downloadContrato(id: number): void {
    const url = `http://localhost:8080/usuarios/contrato/${id}`;
    this.http.get(url, { responseType: 'blob' }).subscribe(
      (response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = `Contrato_${id}.pdf`;
        link.click();
        this.refreshUsuarios();
      },
      (error) => {
        console.error('Error al descargar el contrato:', error);
        this.snackBar.open('Error al descargar el contrato', 'Cerrar', { duration: 3000 });
      }
    );
  }

  seleccionarUsuario(usuario: any) {
    this.usuarioSeleccionado.emit(usuario);
  }

  editUsuario(element: any) {
    console.log('Editar usuario con ID:', element.id);
    this.seleccionarUsuario(element);
  }

  deleteUsuario(id: number) {
    const apiUrl = `http://localhost:8080/usuarios/eliminar/${id}`;
    this.refreshUsuarios();
    this.http.delete(apiUrl).subscribe(
      (response) => {
        console.log('Usuario eliminado:', response);
      }
    );
  }

}
