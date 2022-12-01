// TODO: Replace this with your own data model type
export class ItemCategory {

    constructor(code: string, name: string){
        this.CategoryCode = code;
        this.CategoryName = name;
    }

    CategoryCode: string;
    CategoryName: string;
    Deleted?: boolean;
    CreatedTimestamp?: string;
    CreatedBy?: string;
    UpdatedTimestamp?: string;
    UpdatedBy?: string;
}
