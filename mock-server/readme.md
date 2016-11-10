Remember to run `$ sh build.sh` first.

# /get-example/

Just reads from the datastore

# /post-example/

Expects a json string as an argument, then prints it. Remember to serialize it.

Ex

    $ ./mock-server/post-example "{\"name\":\"abc\",\"place\":\"xyz\"}"

    > { name: 'abc', place: 'xyz' }

# /watch-example/

Prints the contents of the datastore every time it's updated.
