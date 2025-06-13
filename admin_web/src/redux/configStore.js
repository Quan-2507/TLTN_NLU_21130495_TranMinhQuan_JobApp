import { applyMiddleware, createStore, combineReducers } from "redux";
import reduxThunk from 'redux-thunk';
import { StationReducer } from './reducers/StationReducer';
import { UserReducer } from './reducers/UserReducer';
import { LoadingReducer } from './reducers/LoadingReducer';
import { FAQReducer } from './reducers/FAQReducer';
import { ProfitReducer } from './reducers/ProfitReducer';
import { TripReducer } from './reducers/TripReducer';
import { DriverReducer } from './reducers/DriverReducer';
import { SearchReducer } from './reducers/SearchReducer';
import { PromoteTripReducer } from './reducers/PromoteTripReducer';
import { NewReducer } from './reducers/NewReducer';
import { OfferReducer } from './reducers/OfferReducer';
import { IndustryReducer } from './reducers/IndustryReducer';
import { SkillReducer } from './reducers/SkillReducer';
import { JobTypeReducer } from "./reducers/JobTypeReducer";
import { LevelReducer } from "./reducers/LevelReducer";
import { CityProvinceReducer } from "./reducers/CityProvinceReducer";
import { SubscriptionPlanReducer } from "./reducers/SubscriptionPlanReducer";
import { AccountReducer } from './reducers/AccountReducer';
import { CompanyReducer } from './reducers/CompanyReducer';
import { JobReducer } from './reducers/JobReducer';
import { LocationReducer } from './reducers/LocationReducer';
import { AdvertisementReducer } from './reducers/AdvertisementReducer';








// LevelReducer

const rootReducer = combineReducers({
    AdvertisementReducer,
    SkillReducer,
    IndustryReducer,
    JobTypeReducer,
    LevelReducer,
    CityProvinceReducer,
    LocationReducer,
    SubscriptionPlanReducer,
    UserReducer,
    AccountReducer,
    CompanyReducer,
    JobReducer,
    StationReducer,
    DriverReducer,
    TripReducer,
    SearchReducer,
    LoadingReducer,
    FAQReducer,
    ProfitReducer,
    PromoteTripReducer,
    NewReducer,
    OfferReducer
})

export const store = createStore(rootReducer, applyMiddleware(reduxThunk));