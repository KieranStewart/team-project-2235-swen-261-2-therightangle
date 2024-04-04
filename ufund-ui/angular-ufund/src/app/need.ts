import { Date } from "./date";
import { VolunteerDate } from "./volunteer-date";
import { Tag } from "./tag"

/**
 * A way to store information about a need
 */
export interface Need {
    // Core information
    name: string;
    description: string;
    goal: number;
    progress: number;
    volunteerDates: VolunteerDate[];
    deadline: Date;
    type: String;
    tags: String[];

    // Client-exclusive state
    inFundingBasket: boolean;
    donationAmount: number;
    selectedVolunteerDates: VolunteerDate[];
}