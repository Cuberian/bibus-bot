require: slotfilling/slotFilling.sc
  module = sys.zb-common

theme: /

    state: Start
        q!: $regex</start>
        a: Молви, друг, и войди!
        
        state: Melon
            q: melon
            a: Перед тобой три коридора, в какой пойдешь?
        
        state: NoMelon
            event: noMatch
            a: На эльфийском...
    
    state: NoMatch
        event!: noMatch
        a: Попробуйте сказать это на эльфийском
    
   