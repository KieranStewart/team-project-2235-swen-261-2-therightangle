import { Injectable } from '@angular/core';
import { Need } from './need';

/**
 * A service that holds a local copy of the cupboard,
 * along with other pertinent information about the
 * state of the application.
 */
@Injectable({
  providedIn: 'root'
})
export class NeedCacheService {

  constructor() { }

  /**
   * Keeps track of the need that is being displayed currently.
   */
  selectedNeed!: Need | null;
  cupboard!: Need[] | null;

  /**
   * Not finished.
   */
}
