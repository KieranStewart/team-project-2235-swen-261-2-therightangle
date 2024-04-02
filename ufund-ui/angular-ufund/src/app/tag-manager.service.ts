import { Injectable } from '@angular/core';
import { Tag } from "./tag"
import { TagService } from './tags.service';
import { Observable, Subscriber } from 'rxjs';

/**
 * This service handles tags relative to user accounts and needs
 */
@Injectable({
  providedIn: 'root'
})
export class TagManagerService {
  publicTag!: Tag;

  constructor(private tagService: TagService) { }

//   getPublicTag(): Tag{
//     const that = this;
//     var response: Observable<string>;

//     this.tagService.getTag("public").subscribe({
//       next(publicTag) {
//         that.publicTag = publicTag;
//       }
//     })
//     return this.publicTag;
// }
}
