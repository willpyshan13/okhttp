package okhttp3.recipes

import okhttp3.OkHttpClient
import okhttp3.Request.Builder
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.internal.concurrent.TaskRunner
import okio.ByteString
import java.util.concurrent.TimeUnit

class WebSocketCompression : WebSocketListener() {
  private fun run() {
    val client = OkHttpClient()
    val request = Builder()
        .url("ws://localhost:8080/")
        .build()
    client.newWebSocket(request, this)
    // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
//client.dispatcher().executorService().shutdown();
  }

  override fun onOpen(
    webSocket: WebSocket,
    response: Response
  ) {
    for (header in response.headers) {
      println(header)
    }
    println()

    val queue = TaskRunner.INSTANCE.newQueue()
    queue.schedule(name = "ping") {
      val message = "The unexpected is our normal routine. Maybe we better talk out here; the observation lounge has turned into a swamp. Congratulations - you just destroyed the Enterprise. Is it my imagination, or have tempers become a little frayed on the ship lately? Ensign Babyface! and attack the Romulans. Some days you get the bear, and some days the bear gets you. Travel time to the nearest starbase? This is not about revenge. This is about justice. But the probability of making a six is no greater than that of rolling a seven. We finished our first sensor sweep of the neutral zone. We know you're dealing in stolen ore. But I wanna talk about the assassination attempt on Lieutenant Worf. Yes, absolutely, I do indeed concur, wholeheartedly! When has justice ever been as simple as a rule book? When has justice ever been as simple as a rule book? Mr. Worf, you do remember how to fire phasers? We have a saboteur aboard. About four years. I got tired of hearing how young I looked. Wouldn't that bring about chaos? What's a knock-out like you doing in a computer-generated gin joint like this? Sorry, Data. Worf, It's better than music. It's jazz. And blowing into maximum warp speed, you appeared for an instant to be in two places at once. You enjoyed that. I'm afraid I still don't understand, sir. Did you come here for something in particular or just general Riker-bashing? Damage report!\n The unexpected is our normal routine. Maybe we better talk out here; the observation lounge has turned into a swamp. Congratulations - you just destroyed the Enterprise. Is it my imagination, or have tempers become a little frayed on the ship lately? Ensign Babyface! and attack the Romulans. Some days you get the bear, and some days the bear gets you. Travel time to the nearest starbase? This is not about revenge. This is about justice. But the probability of making a six is no greater than that of rolling a seven. We finished our first sensor sweep of the neutral zone. We know you're dealing in stolen ore. But I wanna talk about the assassination attempt on Lieutenant Worf. Yes, absolutely, I do indeed concur, wholeheartedly! When has justice ever been as simple as a rule book? When has justice ever been as simple as a rule book? Mr. Worf, you do remember how to fire phasers? We have a saboteur aboard. About four years. I got tired of hearing how young I looked. Wouldn't that bring about chaos? What's a knock-out like you doing in a computer-generated gin joint like this? Sorry, Data. Worf, It's better than music. It's jazz. And blowing into maximum warp speed, you appeared for an instant to be in two places at once. You enjoyed that. I'm afraid I still don't understand, sir. Did you come here for something in particular or just general Riker-bashing? Damage report!\n The unexpected is our normal routine. Maybe we better talk out here; the observation lounge has turned into a swamp. Congratulations - you just destroyed the Enterprise. Is it my imagination, or have tempers become a little frayed on the ship lately? Ensign Babyface! and attack the Romulans. Some days you get the bear, and some days the bear gets you. Travel time to the nearest starbase? This is not about revenge. This is about justice. But the probability of making a six is no greater than that of rolling a seven. We finished our first sensor sweep of the neutral zone. We know you're dealing in stolen ore. But I wanna talk about the assassination attempt on Lieutenant Worf. Yes, absolutely, I do indeed concur, wholeheartedly! When has justice ever been as simple as a rule book? When has justice ever been as simple as a rule book? Mr. Worf, you do remember how to fire phasers? We have a saboteur aboard. About four years. I got tired of hearing how young I looked. Wouldn't that bring about chaos? What's a knock-out like you doing in a computer-generated gin joint like this? Sorry, Data. Worf, It's better than music. It's jazz. And blowing into maximum warp speed, you appeared for an instant to be in two places at once. You enjoyed that. I'm afraid I still don't understand, sir. Did you come here for something in particular or just general Riker-bashing? Damage report!\n"
      webSocket.send(message)
      return@schedule TimeUnit.MILLISECONDS.toNanos(2_000L)
    }

    queue.schedule(name = "short", delayNanos = TimeUnit.MILLISECONDS.toNanos(1000L)) {
      val message = "short message"
      webSocket.send(message)
      return@schedule TimeUnit.MILLISECONDS.toNanos(2_000L)
    }
  }

  override fun onMessage(
    webSocket: WebSocket,
    text: String
  ) {
    println("MESSAGE: ${text.substring(0, minOf(text.length, 100))}")
  }

  override fun onMessage(
    webSocket: WebSocket,
    bytes: ByteString
  ) {
    println("MESSAGE: " + bytes.hex())
  }

  override fun onClosing(
    webSocket: WebSocket,
    code: Int,
    reason: String
  ) {
    webSocket.close(1000, null)
    println("CLOSE: $code $reason")
  }

  override fun onFailure(
    webSocket: WebSocket,
    t: Throwable,
    response: Response?
  ) {
    t.printStackTrace()
  }

  companion object {
    @JvmStatic fun main(args: Array<String>) {
      WebSocketCompression().run()
    }
  }
}
