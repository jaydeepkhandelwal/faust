# faust


I've used mysql because -

1) Atomicity in batch operations is our primary concern.
2) Data is not huge and can be stored on one machine.
3) Friendfeed and Pinterest are already using mysql for their schema-less data. Ref (https://backchannel
.org/blog/friendfeed-schemaless-mysql)



What if most of the events have a field called "rank" and we want to retrieve max ranked
record?

To achieve it, we create another table called index_rank with following columns -

    CREATE TABLE index_rank_id (
        rank int(11) NOT NULL,
        record_id BIGINT(20) NOT NULL UNIQUE,
        PRIMARY KEY (rank, record_id)
    ) ENGINE=InnoDB;


And you can get the record_id for max rank from this table and whole record from record table.





I've used Dropwizard to build rest-apis as it's light weight.

Haven't used hibernate to keep the project simple as there's only one table.


Config File - /src/main/resources/Faust.yml
Sql Schema - faust.sql
DB name - faust


APIS signature -

Swagger Doc - http://localhost:5400/swagger

1) curl -i -X GET \
 'http://localhost:5400/v1/read?id=13&id=14'    - returns records with given ids if available.

2) curl -i -X DELETE \
    'http://localhost:5400/v1/delete?id=13&id=14'  - delete records of given ids if exist.

3) curl -i -X PUT \
      -H "Content-Type:application/json" \
      -H "Authorization:Basic YWNjOmFjYzEyMw==" \
      -H "token:NavWd64LiluxxZVkNaVyAPUHsoqnfcQ1YpARuj9Z7or3COA64HNNav" \
      -d \
   '[
    {

     "type": "user_add",
     "event": {"x": "y", "a": "b"}
   },
     {
       "id":19,
     "type": "user_add",
       "event": {"x": "z", "a": "c", "d": "e"}
   }
     ]' \
    'http://localhost:5400/v1/upsert'

 It insert/updates the record.  If id exists, it updates otherwise inserts. If any of the operation fails, it rolls
 back.





