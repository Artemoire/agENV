import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-agent-start-form',
  templateUrl: './agent-start-form.component.html',
  styleUrls: ['./agent-start-form.component.css']
})
export class AgentStartFormComponent implements OnInit {

  form: FormGroup;
  loading: boolean = false;
  errorMessage: string;

  constructor(
    private fb: FormBuilder,
  ) { }

  ngOnInit() {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      type: ['', Validators.required]      
    });
  }

  onStart() {
    console.log("kul");
  }

}
