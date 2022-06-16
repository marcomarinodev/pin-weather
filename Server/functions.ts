import { readFileSync, utimes, writeFileSync } from "fs";
import { join } from 'path';
import { WeatherEntries, Users, WeatherEntry, User, Tokens, Locations, Posts, Location } from "./dbreader";
import {v4 as uuidv4} from 'uuid';

const syncReadFile = (filename: string) => {
    return readFileSync(join(__dirname, filename), 'utf-8');
}

const syncWriteFile = (filename: string, data: string) => {
    return writeFileSync(join(__dirname, filename), data, 'utf-8')
}

const weatherEntries: WeatherEntries = JSON.parse(syncReadFile('./db/weather-entries.json'))
const users: Users = JSON.parse(syncReadFile('./db/users.json'))
const tokens: Tokens = JSON.parse(syncReadFile('./db/tokens.json'))
const locations: Locations = JSON.parse(syncReadFile('./db/locations.json'))
const posts: Posts = JSON.parse(syncReadFile('./db/posts.json'))


const weatherEntry = (args: {id: number}) : WeatherEntry | undefined =>
    weatherEntries.data.find(e => e.id === args.id)

const allWeatherEntries = (args: { token: string }) : WeatherEntry[] => {
    let user = users.data.find(u => u.token === args.token)
    if (user === undefined) {
        console.log("invalid token")
        return []
    }
    var entries: WeatherEntry[] = []

    user.favourites.forEach(favPlaceId => {
        let entry = weatherEntries.data.find(place => place.id === favPlaceId)
        if (entry !== undefined) entries.push(entry)
    })
    
    return entries
}

const allLocations = () : Location[] => locations.data

const addPost = (args: {token: string, bitmap: string, lat: number, lon: number}): Boolean => {
    let user = users.data.find(u => u.token === args.token)
    if (user === undefined) { 
        console.log("invalid token")
        return false
    }

    let locationId = uuidv4()
    let postId = uuidv4()

    let location = {
        id: locationId,
        lat: args.lat,
        lon: args.lon
    }

    let post = {
        id: postId,
        bitmap: args.bitmap,
        locationId: locationId
    }

    locations.data.push(location)
    posts.data.push(post)
    
    
    syncWriteFile('./db/locations.json', JSON.stringify(locations, null, 2))
    syncWriteFile('./db/posts.json', JSON.stringify(posts, null, 2))

    return true
}

const queriedEntries = (args: { token: string, query: string }): WeatherEntry[] => {
    let user = users.data.find(u => u.token === args.token)
    if (user === undefined) { 
        console.log(args.token)
        console.log("invalid token")
        return []
    }
    var entries: WeatherEntry[] = []

    weatherEntries.data.forEach(place => {
        if (place.city.startsWith(args.query)) {
            entries.push(place)
        }
    })

    return entries
}

const addFavPlace = (args: { token: string, id: string }): Boolean => {
    let user = users.data.find(u => u.token === args.token)
    if (user === undefined) {
        console.log("invalid token")
        return false
    }
    let intId = parseInt(args.id)
    if (user.favourites.find(p => p === intId) === undefined) user.favourites.push(intId)

    syncWriteFile('./db/users.json', JSON.stringify(users, null, 2))

    return true
}

const allUsers = () : User[] => users.data

const registerUser = (args: {
    fullName: string,
    email: string,
    password: string
}) : User | undefined => {

    console.log("new registration request")

    if (users.data.find(u => u.email === args.email) !== undefined) {
        return undefined
    }

    let user = { 
        id: uuidv4(),
        favourites: Array<number>(0),
        posts: Array<number>(0),
        fullName: args.fullName,
        email: args.email,
        password: args.password,
        token: uuidv4()
    }

    users.data.push(user)
    syncWriteFile('./db/users.json', JSON.stringify(users, null, 2)) 

    return login({ email: args.email, password: args.password})
}

const login = (args: {
    email: string,
    password: String
}) : User | undefined => {
    
    let user = users.data.find(u => u.email === args.email)

    if (user !== undefined && user.password === args.password) {
        // returns a token
        let token = uuidv4()
        tokens.tokens.push(token)
        syncWriteFile('./db/tokens.json', JSON.stringify(tokens, null, 2))
        
        user.token = token
        syncWriteFile('./db/users.json', JSON.stringify(users, null, 2))

        return user
    }

    return undefined
}

const validateToken = (args: { token: string }) : boolean => {
    console.log("validate token")
    return tokens.tokens.find(t => t === args.token) !== undefined
}

const deleteToken = (args: { token: string }) : string => {

    tokens.tokens = tokens.tokens.filter((element, index, array) => {
        return element !== args.token
    })

    let user = users.data.find(u => u.token === args.token)

    if (user !== undefined) {
        user.token = ""
    }

    syncWriteFile('./db/tokens.json', JSON.stringify(tokens, null, 2))
    syncWriteFile('./db/users.json', JSON.stringify(users, null, 2))

    return "Deleted"
}

const deleteFavPlace = (args: {token: string, id: string}): Boolean => {
    let user = users.data.find(u => u.token === args.token)
    if (user === undefined) {
        console.log("invalid token")
        return false
    }

    let intId = parseInt(args.id)
    user.favourites = user.favourites.filter((element, index, array) => {
        return element !== intId
    })

    syncWriteFile('./db/users.json', JSON.stringify(users, null, 2))

    return true
}

export const root = {
    weatherEntry,
    allWeatherEntries,
    allUsers,
    registerUser,
    login,
    validateToken,
    deleteToken,
    queriedEntries,
    addFavPlace,
    deleteFavPlace,
    allLocations,
    addPost
}