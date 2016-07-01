import sys
from nyamuk import *

import config


def nloop(client):
    client.packet_write()  # flush write buffer (messages sent to MQTT server)
    client.loop()  # fill read buffer   (enqueue received messages)
    return client.pop_event()  # return 1st received message (dequeued)


client = Nyamuk(config.CLIENT_ID, server=config.BROKER)
ret = client.connect(version=3)
ret = nloop(client)  # ret should be EventConnack object
if not isinstance(ret, EventConnack) or ret.ret_code != 0:
    print 'connection failed';
    sys.exit(1)

client.publish(config.QUEUE, 'this is a test', qos=1)
ret = nloop(client)  # ret should be EventPuback

client.disconnect()
