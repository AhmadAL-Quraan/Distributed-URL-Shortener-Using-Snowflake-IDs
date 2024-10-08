package Projects.Distributed.URL.Service;


import org.springframework.stereotype.Service;

@Service
public class SnowFlackIdGenerator {

   //parts of 64 bit ID
   private final long datacenterIdBits= 5L ;
   private final long workerIdBits= 5L ;
   private final long sequenceBits = 12L ;
   //max each part in decimal
   private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
   private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
   private final long maxSequence = -1L ^ (-1L << sequenceBits);

   //Places of the parts in the 64 bit place
   private final long workerIdShift = sequenceBits;
   private final long datacenterIdShift = sequenceBits + workerIdBits;
   private final long timestampShift = sequenceBits + workerIdBits + datacenterIdBits;

   //twitter default epoch -- > which will be used to cnt the time in millisecond for the id
   private final long epoch=1288834974657L;

   private final long workerId;
   private final long datacenterId;
   private long sequence = 0L;
   private long lastTimestamp = -1L;



   /*
   Note: in the context of distributed systems and Snowflake ID generation, "different machines"
   typically refer to different servers (or nodes) in the system. Each server, or worker node, is assigned a unique workerId to ensure that IDs
   generated across multiple servers are distinct, even if they are generated at the same time.
    */

   //constructor
   public SnowFlackIdGenerator() {
      this.workerId=1L;
      this.datacenterId=1L;
   }

   private long waitForNextMillis(long lastTimestamp) {
      long timestamp = System.currentTimeMillis();
      while (timestamp <= lastTimestamp) {
         timestamp = System.currentTimeMillis();
      }
      return timestamp;
   }

   /*

The synchronized keyword in Java is used to control access to a method or block of code
by multiple threads. It ensures that only one thread can execute a synchronized
method or block at a time, preventing race conditions or
data corruption when multiple threads try to access shared resources simultaneously.
    */
   public synchronized long nextId() {
      long currentTimestamp = System.currentTimeMillis();

      if (currentTimestamp < lastTimestamp) {
         throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
      }
     // handle if the current Timetamp in millisecond== previout timestamp millisecond
      /*
      Sequence number: For every number generated on that machine, the sequence number
         is incremented by 1.
         The number is reset to 0 every millisecond.

         Sequence: A counter to generate multiple IDs within the same millisecond.
      */
      if (currentTimestamp == lastTimestamp) {

         sequence = (sequence + 1) & maxSequence;
         //if sequence reached the maximum it will wait for next millisecond
         if (sequence == 0) {
            currentTimestamp = waitForNextMillis(lastTimestamp);
         }
      } else {
         sequence = 0L;
      }

      lastTimestamp = currentTimestamp;
     /*
      Combines the different parts of the Snowflake ID (timestamp, datacenter ID, worker ID, and sequence)
   into a single 64-bit ID by performing a bitwise OR operation between them.
      */
      return ((currentTimestamp - epoch) << timestampShift)
              | (datacenterId << datacenterIdShift)
              | (workerId << workerIdShift)
              | sequence;
   }
}
