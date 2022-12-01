// TODO: Replace this with your own data model type
export class Uom {

    constructor(uomId: string, uomDescription: string){
        this.UomId = uomId;
        this.UomDescription = uomDescription;
    }

    UomId?: string;
    UomDescription?: string;
    Deleted?: boolean;
    CreatedTimestamp?: string;
    CreatedBy?: string;
    UpdatedTimestamp?: string;
    UpdatedBy?: string;
}
