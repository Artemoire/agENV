import { Injectable } from '@angular/core';

@Injectable()
export class ConversationService {

  private messageCache: any = {};
  data: any;
  loaded: false;

  constructor() { }

}
