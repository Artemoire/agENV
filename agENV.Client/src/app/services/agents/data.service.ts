import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { backend } from '../../../settings';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  types: any[];

  constructor(
    http: HttpClient
  ) {
    http.get<any[]>(`${backend}/agents/classes`).subscribe(x => {
      this.types = x;
    })
  }


}
