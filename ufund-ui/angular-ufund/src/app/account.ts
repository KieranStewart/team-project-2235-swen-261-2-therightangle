import { Tag } from "./tag"

/**
 * A way to store information about an account
 */
export interface Account{
    name: String;
    password: String;
    email: String;
    tags: String[];
    isAdmin: boolean;
}