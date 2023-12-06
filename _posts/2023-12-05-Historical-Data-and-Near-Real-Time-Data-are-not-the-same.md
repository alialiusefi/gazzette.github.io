---
title: "Historical Data and Near Real Time Data are not the same!"
excerpt_separator: "<!--more-->"
categories:
  - Blog
tags:
  - scalability
  - batch
  - integration
  - performance
---

When extracting data from a third party api service that generates a lot of data - the amount of work needed to then maintain the stored data starts to compound. Bottlenecks will prevent scaling and loss of revenue may occur, so to mitigate this issue, it is good to ask ourselves the following questions:

- In the context of a business domain, Do we really need to treat all data the same? 

For example, do old orders get updated as often as recent orders, or do old orders even need to be updated at all? 

**Key Idea**: Data that doesn't change often after some period of time can be updated much rarer, for example nightly instead of every 1 hr. For data that has reached its final state, can be simply archived or deleted.

-  Do we need to fetch new data at the same time as updating the existing data? 

For example, the amount of data to fetch new orders in the last 5 minutes can be much less comparing to updating existing orders for the last 60 days. Doing that in the same job may lead to delays in new data processing throughput.

**Key Idea**: Updating existing data and fetching new data independently result into a more performant near real time integration.  

- Do we need to have an iterator approach when updating existing data?

When data is already stored in database we already have direct access to objects by its identifier. This means we can parallelize the update process by calling the data store (an api for example) in batches.
 
**Key Idea**: Check your data supplier API and utilize it correctly with multiple threads. Just be careful with not hitting the rate limit ;).


Some food for thought :).

