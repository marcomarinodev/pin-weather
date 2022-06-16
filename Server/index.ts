import express from "express";
import { json } from "body-parser";
import { graphqlHTTP } from "express-graphql";

import { customSchema } from "./schema";
import { root } from "./functions";

const app = express();
const port = 8000;

type ReqQuery = { token ?: string }

app.use(json());
app.use('/graphql', graphqlHTTP({
    schema: customSchema,
    rootValue: root,
    graphiql: true
}));

app.listen(8000, () => {
    console.log('Running a GraphQL API server at http://localhost:8000/graphql');
    console.log("Listening on port: " + port);
});

// Default response for any other request
app.use((req, res) => {
    res.status(404);
});