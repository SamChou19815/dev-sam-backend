package com.developersam.rss

import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import java.net.URL

/**
 * [FeedParser] is responsible for parsing the feed.
 */
internal object FeedParser {

    /**
     * [parse] parses the rss feed in the given [url] and returns the parsed result, which may be
     * `null` to indicate a failure.
     */
    @JvmStatic
    fun parse(url: String): Pair<Feed, List<FeedItem>>? {
        val rawFeed: SyndFeed = try {
            SyndFeedInput().build(XmlReader(URL(url)))
        } catch (e: Exception) {
            return null
        }
        val feed = kotlin.run {
            val title = rawFeed.title ?: return null
            val link = rawFeed.link ?: return null
            val description = rawFeed.description ?: ""
            Feed(rssUrl = url, title = title, link = link, description = description)
        }
        val nowTime = System.currentTimeMillis()
        val items = rawFeed.entries?.mapNotNull { entry ->
            val title = entry.title ?: return@mapNotNull null
            val link = entry.link ?: return@mapNotNull null
            val description = when {
                entry.description != null -> entry.description.value
                entry.contents != null && entry.contents.isNotEmpty() ->
                    entry.contents.joinToString(separator = "\n") { it.value }
                else -> return@mapNotNull null
            }
            val publicationTime = entry.publishedDate?.time ?: nowTime
            FeedItem(
                    title = title, link = link,
                    description = description, publicationTime = publicationTime
            )
        } ?: return null
        return feed to items
    }

}
