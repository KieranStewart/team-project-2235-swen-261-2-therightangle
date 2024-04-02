import { Component, Input, OnInit } from "@angular/core";
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
export class NeedEditComponent implements OnInit {

    constructor(private needService: NeedService) {}

    ngOnInit(): void {
        // Fallback code for if something is null in the backend
        if(this.currentNeed != null) {
            if(this.currentNeed.deadline == null) {
                this.currentNeed.deadline = {day: 0, month: 0, year: 0};
            }
            if(this.currentNeed.description == null) {
                this.currentNeed.description = "";
            }
            if(this.currentNeed.volunteerDates == null) {
                this.currentNeed.volunteerDates = [];
            }
            if(this.currentNeed.type == null) {
                this.currentNeed.type = "donation";
            }
        }
    }

    @Input() currentNeed!: Need;
    errorText: string = "";

    updateNeed(): void {

    }

    changeName(newName: string): void {
        this.currentNeed.name = newName;
        this.update();
    }

    changeGoal(newGoal: number): void {
        if(newGoal < 0) {

        }
        this.currentNeed.goal = newGoal;
        this.update();
    }

    changeDescription(newDescription: string) {
        this.currentNeed.description = newDescription;
        this.update();
    }

    update(): void {
        this.needService.updateNeed(this.currentNeed).subscribe().unsubscribe();
    }
}