// TODO: Replace this with your own data model type
export class HsnCode {

    constructor(code: string, description: string, taxSlab: number){
        this.HsnCode = code;
        this.HsnCodeDescription = description;
        this.TaxSlab = taxSlab;
    }

    HsnCode?: string;
    HsnCodeDescription?: string;
    TaxSlab?: number;
    Deleted?: boolean;
    CreatedTimestamp?: string;
    CreatedBy?: string;
    UpdatedTimestamp?: string;
    UpdatedBy?: string;
}
