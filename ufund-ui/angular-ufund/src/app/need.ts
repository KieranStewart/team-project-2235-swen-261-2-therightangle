import { Date } from "./date";
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
    volunteerDates: Date;
    deadline: Date;
    type: String;
    tags: String[];

    // Client-exclusive state
    inFundingBasket: boolean;
    donationAmount: number;
}