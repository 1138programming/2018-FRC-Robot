#include <Adafruit_NeoPixel.h>
#include <Wire.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif


// IMPORTANT: To reduce NeoPixel burnout risk, add 1000 uF capacitor across
// pixel power leads, add 300 - 500 Ohm resistor on first pixel's data input
// and minimize distance between Arduino and first pixel.  Avoid connecting
// on a live circuit...if you must, connect GND first.


#define PIN 6
#define NUM_PIXELS 40
// Parameter 1 = number of pixels in strip
// Parameter 2 = Arduino pin number (most are valid)
// Parameter 3 = pixel type flags, add together as needed:
//   NEO_KHZ800  800 KHz bitstream (most NeoPixel products w/WS2812 LEDs)
//   NEO_KHZ400  400 KHz (classic 'v1' (not v2) FLORA pixels, WS2811 drivers)
//   NEO_GRB     Pixels are wired for GRB bitstream (most NeoPixel products)
//   NEO_RGB     Pixels are wired for RGB bitstream (v1 FLORA pixels, not v2)
//   NEO_RGBW    Pixels are wired for RGBW bitstream (NeoPixel RGBW products)
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_PIXELS, PIN, NEO_GRB + NEO_KHZ800);

// Blinking variables. 
//   <ordinal>Color = Main color of the LED lights
//   <ordinal>ColorBlink = Secondary color of the LED lights,
//                         used when (millis() / blinkTime) mod 2
//                         returns a nonzero value, aka every
//                         <blinkTime> milliseconds
//   blinkTime = Time between color change for each LED section.

int blinkTime; // Time before light update
uint32_t color;
uint32_t secondColor;
uint32_t temp_color;

int timesBlunk;
int pixel_offset;

// The different lighting modes that can be used for
// determining how to light the LEDs. Mirrored in
// LEDSubsystem.java file in the Keystone code
typedef enum TLightModes {
  Off = 0,
  Idle,
  Cube,
  Rung,
  NumberOfModes,
} TLightModes;

// Possible responses that we can return after a command
// Either success (0), or Error (1). Error is currently
// not returned, but there is a possibility that we might
// have to use it in the future, and it's here for that
// purpose.
typedef enum Responses {
  Success = 0,
  Error
} Responses;

// Keeps track of the current mode that we're using.
TLightModes currentMode = Cube;
TLightModes modeToUse;         // Which mode to use. 

#define ONBOARDLED 13

void setup() {
  // This is for Trinket 5V 16MHz, you can remove these three lines if you are not using a Trinket
  #if defined (__AVR_ATtiny85__)
    if (F_CPU == 16000000) clock_prescale_set(clock_div_1);
  #endif
  // End of trinket special code

  // ADAFRUIT NEOPIXEL INITIALIZATION
  strip.begin();
  strip.show(); // Initialize all pixels to 'off'

  // Initialize the color variables
  color       = strip.Color(255, 36, 0);
  secondColor = strip.Color(0, 0, 255);
  blinkTime   = 100;
  timesBlunk  = -1;

  // I2C INITIALIZATION - Address: 1138
  Wire.begin(0x04);             // join i2c bus with address #4
  // Todo: Stop being an idiot, Edward
  Wire.onReceive(receiveEvent); // register event
  Wire.onRequest(requestEvent);

  // Serial over USB for debugging
  Serial.begin(9600);
  Serial.write("Startup.\n");

  pinMode(ONBOARDLED, OUTPUT);

}

void updateLED() {
  // This updates the LED strip
  for (int currentPixel = 0; currentPixel < NUM_PIXELS; currentPixel++) {
    if (((currentPixel + pixel_offset) / 5) % 2) {
      strip.setPixelColor(currentPixel, color);
    } else {
      strip.setPixelColor(currentPixel, secondColor);
    }
  }
  // strip.show() - Used in the main loop instead
}

Responses status = Success;

/*
 * Event handler for receiving I2C data
 * 
 * We receive 1 byte at the moment, and use that to control which mode 
 * the LEDs will be put in
 */
void receiveEvent(int numBytes)
{
  Serial.write("RECEIVING...\n");
  // Make sure we have 1 byte to read
  if (numBytes < 1) return;
  digitalWrite(ONBOARDLED, HIGH);
  // Read the values from the I2C connection
  modeToUse = Wire.read();
  if (currentMode == modeToUse) {
    // Cry in the corner
    Wire.write(Error);
    Serial.write("Not changing.\n");
    return;
  }
  if (modeToUse == Off || modeToUse == Idle || modeToUse == Cube || modeToUse == Rung) {
    currentMode = modeToUse;
    if (modeToUse == Cube || modeToUse == Rung) {
      color = strip.Color(0, 255, 0);
      secondColor = strip.Color(0, 255, 0);
      timesBlunk = 6;
      blinkTime = 100;
    } else if (modeToUse == Idle) {
      color = strip.Color(255, 36, 0);
      secondColor = strip.Color(0, 0, 255);
      timesBlunk = -1;
      blinkTime = 100;
    } else {
      color = 0;
      secondColor = 0;
      timesBlunk = -1;
    }
    status = Success;
    Serial.write("Success!\n");
  } else {
    status = Error;
    Serial.write("ERROR: ");
    Serial.write(modeToUse);
    Serial.write("\n");
  }
  delay(100);
  digitalWrite(ONBOARDLED, LOW);
}

void requestEvent() {
  Wire.write(status);
}


/* 
 *  The main loop - called repeatedly from main()
 *  This first updates all the LED sections on the strip,
 *  then shows it
 */
void loop() {
  delay(blinkTime);
  pixel_offset = (pixel_offset + 1) % 10;
  if (timesBlunk > 0) {
    timesBlunk--;
    if (modeToUse == Cube || modeToUse == Rung) {
      if (color) {
        temp_color = color;
        secondColor = 0;
        color = 0;
      } else {
        color = temp_color;
        secondColor = temp_color;
        temp_color = 0;
      }
    }
  } else if (timesBlunk == 0) {
    delay(500);
    modeToUse = Idle;
    color = strip.Color(255, 36, 0);
    secondColor = strip.Color(0, 0, 255);
    timesBlunk = -1;
    blinkTime = 100;
  }
  updateLED();
  strip.show();
  if (Wire.available()) {
    Serial.write("!!");
  }
}

void serialEvent() {
  if (Serial.available()) {
    digitalWrite(ONBOARDLED, HIGH);
    Serial.read();
    //digitalWrite(ONBOARDLED, LOW);
  }
}

