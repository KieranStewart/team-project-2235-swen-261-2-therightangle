import { Component, Input } from "@angular/core";
import { Need } from "../need";
import { NeedService } from "../need.service";

/**
 * Manager is able to make changes to need
 */
@Component({
    selector: 'app-need-edit',
    templateUrl: './need-edit.component.html',
    styleUrls: ['./need-edit.component.css']
})

export class NeedEditComponent{
    constructor(private needService: NeedService) {}

    @Input()
    currentNeed!: Need;

    changeName(newName: string): void{
        this.currentNeed.name = newName;
        this.needService.updateNeed
    }

    changeGoal(newGoal: number): void{
        this.currentNeed.goal = newGoal;
        this.needService.updateNeed;
    }

}