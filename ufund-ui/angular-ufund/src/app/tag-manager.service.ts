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
  // publicTag!: Tag;
  currentTag!: Tag;
  contents: Tag[];
  tagStringList: String[];

  constructor(private tagService: TagService) {
    this.contents = [];
    this.tagStringList = [];
    this.tagStringList = this.getList();
  }

  getList(): String[] {
    this.tagStringList = [];
    const that = this;
    this.tagService.getTags().subscribe({
      next(contents) {
        that.contents = contents;
      }
    })
    this.contents.forEach(tag => {
      this.tagStringList.push(tag.name)
    });
    return this.tagStringList
  }

  getTag(name: String): Tag {
    this.contents.forEach(tag => {
      if (tag.name == name) {
        this.currentTag = tag;
      }
    });
    return this.currentTag;
  }
}
