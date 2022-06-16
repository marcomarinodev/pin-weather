export interface WeatherEntries {
    data: WeatherEntry[]
}

export interface Users {
    data: User[]
}

export interface Locations {
    data: Location[]
}

export interface Posts {
    data: Post[]
}

export interface Tokens {
    tokens: string[]
}

export interface WeatherEntry {
    id: number,
    city: string,
    temp: number,
    time: string,
    status: string,
    max: number,
    min: number
}

export interface User {
    id: string,
    favourites: number[]
    posts: number[]
    fullName: string,
    email: string,
    password: string,
    token: string
}

export interface Post {
    id: string,
    bitmap: string,
    locationId: string
}

export interface Location {
    id: string,
    lat: number,
    lon: number
}