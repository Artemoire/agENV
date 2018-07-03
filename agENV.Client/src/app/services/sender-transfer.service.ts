import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SenderTransferService {

  receivers: any[];

  constructor() { }

  set(receivers: any[]) {
    this.receivers = receivers;
  }

  get() {
    let ret = this.receivers;
    this.receivers = undefined;
    return ret;
  }
}
