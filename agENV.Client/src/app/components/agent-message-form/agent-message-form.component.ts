import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-agent-message-form',
  templateUrl: './agent-message-form.component.html',
  styleUrls: ['./agent-message-form.component.css']
})
export class AgentMessageFormComponent implements OnInit {

  form: FormGroup;
  loading: boolean = false;
  errorMessage: string;

  constructor(
    private fb: FormBuilder,
  ) { }

  ngOnInit() {
    this.form = this.fb.group({    
    });
  }

  onMessage() {
    console.log("kul");
  }

}
