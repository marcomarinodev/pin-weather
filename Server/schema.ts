import { buildSchema } from "graphql";

export const customSchema = buildSchema(`
    type WeatherEntry {
        id: ID!
        city: String!
        temp: Float!
        time: String!
        status: String!
        max: Float
        min: Float
    }

    type User {
        id: ID!
        favourites: [ID]
        posts: [ID]
        fullName: String!
        email: String!
        password: String!
        token: String
    }

    type Location {
        id: ID!
        lat: String
        lon: String
    }

    type Post {
        id: ID!
        author: String
        bitmap: String
        locationId: ID
    }

    type Query {
        weatherEntry(id: ID): WeatherEntry
        allWeatherEntries(token: String): [WeatherEntry]
        allUsers: [User]
        validateToken(token: String): Boolean
        queriedEntries(token: String, query: String): [WeatherEntry]
        allLocations: [Location]
    }

    type Mutation {
        registerUser(
            fullName: String!,
            email: String!,
            password: String!
            ) : User

        login(email: String!, password: String!): User

        deleteToken(token: String!): String

        addFavPlace(token: String, id: String): Boolean

        deleteFavPlace(token: String, id: String): Boolean

        addPost(token: String, bitmap: String, lat: Float, lon: Float): Boolean
    }

`);
