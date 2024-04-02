import { Component } from '@angular/core';
import { Tag } from '../tag';
import { TagService } from '../tags.service'


@Component({
  selector: 'app-tag-creation',
  templateUrl: './tag-creation.component.html',
  styleUrls: ['./tag-creation.component.css']
})
export class TagCreationComponent {
  errorMessage = '';
  submitted = false;
  newTag!: Tag;
  applyable = false;

constructor(private tagService: TagService) {}


createTag(nameInput: string, tagDescriptionInput: string,tagInstructionInput: string): void{
  const that = this;
  if(nameInput == '' || tagDescriptionInput == ''){
    this.errorMessage = "Please fill in all the boxes"
  } else if (this.applyable && tagInstructionInput == ''){
    this.errorMessage = "Please fill in all the boxes"
  }else {
  this.newTag = {name: nameInput, tagDetail: tagDescriptionInput, tagInstruction: tagInstructionInput, applyable: this.applyable} as Tag;
  this.tagService.addTag(this.newTag).subscribe({
    next(newTag) {
      if(newTag == undefined) {
        that.errorMessage = "Tag name is already taken";
      } else {
        that.newTag = newTag;
        that.submitted = true;
        that.errorMessage = '';
      }
    }
  })
}
}

toggleApplyable(): void{
  this.applyable = !this.applyable;
}

}
