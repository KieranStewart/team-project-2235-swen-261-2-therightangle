import { Date } from "./date";

export interface Need {
    name: String;
    description: String;
    goal: number;
    progress: number;
    volunteerDates: Date;
    deadline: Date;
}