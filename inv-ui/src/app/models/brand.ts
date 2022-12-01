// TODO: Replace this with your own data model type
export class Brand {

    constructor(code: string, name: string){
        this.BrandCode = code;
        this.BrandName = name;
    }

    BrandCode?: string;
    BrandName?: string;
    Deleted?: boolean;
    CreatedTimestamp?: string;
    CreatedBy?: string;
    UpdatedTimestamp?: string;
    UpdatedBy?: string;
}
