import { Injectable } from '@angular/core';
import { Tag } from "./tag"
import { TagService } from './tags.service';

/**
 * This service handles tags relative to user accounts and needs
 */
@Injectable({
  providedIn: 'root'
})
export class TagManagerService {
  publicTag = "public";

  constructor(private tagService: TagService) { }

  // getPublicTag(): String{
  //   const that = this;
  //   this.tagService.getTag("public").subscribe({
  //     next(publicTag) {
  //       that.publicTag = publicTag.name
  //     }
  //   })
  //   return this.publicTag;
  // }
}
