# multithreading

This is a project of EPAM Java Web Development training.

Multithreading application.

## Task description:

Create a multithreading application that uses shared resources.

Logistic base.

Vans enter the territory of the base for unloading or loading goods that are produced through terminals. If the area is
full then the vans line up outside the base. Vans with perishable goods are served out of turn. Every van must be
serviced.

## Requirements:

- Use synchronization capabilities provided by libraries java.util.concurrent, java.util.concurrent.atomic and
  java.util.concurrent.locks.
- Don't use synchronized, volatile, BlockingQueue.
- Any entity that need to access a shared resource must be a thread.
- Use State template.
- Run threads using ExecutorService.
- Instead of Thread.sleep(), use only the capabilities of the TimeUnit.
- Read object initialization data from file. The data in the file is correct.
- The application must have a thread-safe Singleton. When it was created it is forbidden to use enum.
- Use Log4J2 to write logs.
- To display the work of threads, use the main method.