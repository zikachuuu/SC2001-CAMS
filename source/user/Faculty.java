package source.user;
public enum Faculty {
    NTU , 
    SCSE ,
    SPMS ,
    ADM , 
    EEE , 
    NBS , 
    SSS ;

    // public String ToString()
    // {
    //     return Enum.InternalFormat((RuntimeType)GetType(), GetValue());
    // }

    // private static String InternalFormat(RuntimeType eT, Object value)
    // {
    //     if(!eT.IsDefined(typeof(System.FlagAttribute), false))
    //     {
    //         String retval = GetName(eT, value);
    //         if(retval == null)
    //             return value.ToString();
    //         else
    //             return retval;
    //     }
    //     else
    //     {
    //         return InternalFlagsFormat(eT, value);
    //     }
    // }
}
