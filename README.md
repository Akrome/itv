This repo contains my code for the "Sku" technical challenge for iTV.

This document highlights the interesting bits of the design:

1) Beans:
Basket and SkuId's have been wrapped over their naked Map<String, Integer>, String into custom objects for type safety.
All objects have been made strongly immutable (final + Collections.unmodifiable()).
There is some basic error checking in place, which is also covered in the tests (no negative/zero prices, no negative/
zero quantities, etc).

2) Design
Both Multiprice and "standard" price computation has been implemented via a PricingStrategy interface which, taken
a basket, "consumes" a part of it and tells you how much the consumed bits cost. This design allows to implement future
strategies in a fairly generic sense, as well as allowing to prioritize the application of the strategies among each
other.

3) Input
Standard (price list), Multiprice and Basket are all read as CSV files for the sake of the example. The parsers also
are covered by the tests. The main program reads those 3 strings (filePaths) as args. This interface is bound to change
depending on the system deployment. Basket's csv allow for multiple entries for the same skuId (to simulate entering
them one after the other), while the pricing don't.

That's pretty much all there is. If you have questions, I'm available to discuss them!