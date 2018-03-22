// ---------------------------------------------------------------------------
// Robot NewPing library sketch that does a ping about 20 times per second.
// ---------------------------------------------------------------------------

#include <NewPing.h>
#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIN 6
#define NUM_PIXELS 40
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_PIXELS, PIN, NEO_GRB + NEO_KHZ800);

#define TRIGGER_PIN  8  // Arduino pin tied to trigger pin on the ultrasonic sensor.
#define ECHO_PIN     7  // Arduino pin tied to echo pin on the ultrasonic sensor.
#define MAX_DISTANCE 100// Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.

NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE); // NewPing setup of pins and maximum distance.

void setStripColor(int r, int g, int b) {
  for (int pixel = NUM_PIXELS; pixel > 0; pixel--) {
    strip.setPixelColor(pixel, r, g, b);
  }
  strip.show();
}

void setup() {
  Serial.begin(9600); // Open serial monitor at 9600bt baud to see ping results.
  strip.begin();
  strip.show();
  setStripColor(255, 255, 255);
  Wire.begin(0x04);             // join i2c bus with address #4
  // Todo: Stop being an idiot, Edward
  Wire.onReceive(receiveEvent); // register event
  Wire.onRequest(requestEvent);
}

int len;
void loop() {
  delay(50);                     // Wait 50ms between pings (about 20 pings/sec). 29ms should be the shortest delay between pings.
  len = sonar.convert_cm(sonar.ping_median(3)); // length in centimeters
  // len = sonar.convert_cm(sonar.ping_median(3));
  Serial.print("Ping: ");
  Serial.print(len); // Send ping, get distance in cm and print result (0 = outside set distance range)
  Serial.println("cm");
  setStripColor(min(255 * len / 30, 255), min(36 * len / 30, 255), 0 * len / 30);
}

void receiveEvent(int numBytes)
{
  //Serial.write("RECEIVING...\n");
  // Make sure we have 1 byte to read
  if (numBytes < 1) return;
  while (Wire.available()) {
    Wire.read();
  }
}

void requestEvent() {
  Wire.write(0);
  Wire.write(len < 101 ? len : 0);
  Serial.print("Ultrasonic requested: ");
  Serial.print(len);
  Serial.println();
}
