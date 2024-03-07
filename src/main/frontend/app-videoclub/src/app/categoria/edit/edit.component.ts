import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CategoriaService} from "../categoria.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Categoria} from "../categoria";
import {combineLatest, forkJoin} from "rxjs";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  id: number = 0;
  categoria: Categoria = {} as Categoria;
  form: FormGroup =   new FormGroup({
    categoria:  new FormControl('', [ Validators.required, Validators.pattern('^[a-zA-ZÁáÀàÉéÈèÍíÌìÓóÒòÚúÙùÑñüÜ \-\']+') ])
  });

  constructor(
    public categoriaService: CategoriaService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['idCategoria'];
      this.categoriaService.find(this.id).subscribe((categoria) => {
      this.categoria = categoria;

      this.form.get('nombre')?.setValue(this.categoria.nombre);
    });
  }

  get f(){
    return this.form.controls;
  }

  submit(){
    console.log(this.form.value);
    this.categoriaService.update(this.id, this.form.value).subscribe(res => {
      console.log('Categoría actualizada satisfactoriamente!');
      this.router.navigateByUrl('categoria/index').then();
    })
  }

}
