var SerialPort = require('serialport');

const port = '/dev/ttyUSB0';
const separator = '\n';

var serialport = new SerialPort(port, {
    baudRate: 9600,
    dataBits: 8,
    parity: 'none',
    stopBits: 1,
    flowControl: false,
    parser: new SerialPort.parsers.Readline(separator)
});

serialport.on('error', function(err) {
  console.log('serialport error while opening the port : ' + port, err);
  // process.exit(1);
});

serialport.on('open', function() {
  console.log('serial port opened');
});

module.exports = serialport;
