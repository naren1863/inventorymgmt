import { Brand } from "./brand";
import { HsnCode } from "./hsnCode";
import { ItemCategory } from "./itemCategory";
import { Uom } from "./uom";

// TODO: Replace this with your own data model type
export interface ItemsItem {
    name: string;
    id: number;
    Pk: string;
    ItemId: string;
    ItemName?: string;
    ItemDescription?: string;
    Uom?: Uom;
    Brand?: Brand;
    Category?: ItemCategory;
    HsnCode?: HsnCode;
    CatalogueNumber?: string;
    UnitCost?: number;
    CurrencyCode?: string;

    Deleted?: boolean;
    CreatedTimestamp?: string;
    CreatedBy?: string;
    UpdatedTimestamp?: string;
    UpdatedBy?: string;
}
