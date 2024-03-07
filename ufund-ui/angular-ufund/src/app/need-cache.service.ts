import { Injectable } from '@angular/core';
import { Need } from './need';

@Injectable({
  providedIn: 'root'
})
export class NeedCacheService {

  constructor() { }

  selectedNeed!: Need;
}
