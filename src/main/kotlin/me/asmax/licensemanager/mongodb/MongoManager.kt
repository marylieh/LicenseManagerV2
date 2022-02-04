package me.asmax.licensemanager.mongodb

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import me.asmax.licensemanager.utils.Config
import org.bson.Document

object MongoManager {

    private lateinit var collection: MongoCollection<Document>

    private fun getMongoURL(): String {

        if (Config.getConfig().getString("Mongo.URL") == null) {
            Config.getConfig().set("Mongo.URL", "insertMongoURLHere")
            return "NoMongoDBConnectionStringInstalled"
        }
        return Config.getConfig().getString("Mongo.URL")!!
    }

    private fun getDatabaseString(): String {

        if (Config.getConfig().getString("Mongo.Database") == null) {
            Config.getConfig().set("Mongo.Database", "insertMongoDatabaseNameHere")
            return "NoMongoDBDatabaseDefined"
        }
        return Config.getConfig().getString("Mongo.Database")!!
    }

    fun connect(collectionName: String) {
        val url = getMongoURL()

        val clientURL = MongoClientURI(url)
        val mongoClient = MongoClient(clientURL)

        val mongoDatabase = mongoClient.getDatabase(getDatabaseString())
        collection = mongoDatabase.getCollection(collectionName)

        println("Database connected successfully.")
    }

    fun getDocument(search: Document): Document? {
        return collection.find(search).first()
    }

}