require: functions.js
require: slotfilling/slotFilling.sc
  module = sys.zb-common

theme: /

    state: Start
        q!: $regex</start>
        a: Молви, друг, и войди!
        
        state: Melon
            q: melon
            a: Перед тобой три коридора, в какой пойдешь?
        
        state: GoLeft:
            q: * *лев* *
            a: Тебя съел дракон!
        
        state: GoRight:
            q: * *прав* *
            a: Разрабы - дауны и не придумали эту сюжетную ветку(
        
        state: GoStraight:
            q: * *прям* *
            a: Перед вами сундук. Что будете делать?
            
            state: OpenChest:
                q: * (~открытие/~вскрытие/~взлом) *
                a: Вы получили {{ getRandomInt(10) }} залота!
                
        state: NoMelon
            event: noMatch
            a: На эльфийском...
    
    state: NoMatch
        event!: noMatch
        a: Попробуйте сказать это на эльфийском
    
   