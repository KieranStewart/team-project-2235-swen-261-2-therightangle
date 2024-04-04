import { DateComponent } from "./date/date.component";
export interface VolunteerDate extends DateComponent {
    month: number;
    day: number;
    year: number;
    filled: boolean;
}